package com.agriconnect.config;

import com.agriconnect.entity.Pesticide;
import com.agriconnect.repository.PesticideRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Imports the verified PPQS pesticide CSV.
 *
 * Import is disabled by default.
 *
 * To enable it temporarily, add:
 *
 * agriconnect.pesticide.import.enabled=true
 *
 * to application.properties.
 *
 * Existing pesticide names are never overwritten.
 */
@Component
@ConditionalOnProperty(
        name = "agriconnect.pesticide.import.enabled",
        havingValue = "true"
)
public class PesticideCsvImporter implements CommandLineRunner {

    private final PesticideRepository pesticideRepository;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public PesticideCsvImporter(
            PesticideRepository pesticideRepository
    ) {

        this.pesticideRepository = pesticideRepository;
    }


    // ========================================
    // IMPORT
    // ========================================

    @Override
    public void run(String... args) throws Exception {

        ClassPathResource resource =
                new ClassPathResource(
                        "data/agriconnect_pesticides_initial.csv"
                );


        // ========================================
        // CHECK CSV
        // ========================================

        if (!resource.exists()) {

            System.out.println(
                    "PPQS pesticide CSV not found. Import skipped."
            );

            return;
        }


        int inserted = 0;
        int skipped = 0;
        int malformed = 0;


        // ========================================
        // READ CSV
        // ========================================

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        resource.getInputStream(),
                                        StandardCharsets.UTF_8
                                )
                        )
        ) {

            // Skip header
            reader.readLine();

            String line;


            while ((line = reader.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }


                String[] fields =
                        parseCsvLine(line);


                // ========================================
                // VALIDATE ROW
                // ========================================

                if (fields.length < 9) {

                    malformed++;

                    System.out.println(
                            "Skipping malformed PPQS row: "
                                    + line
                    );

                    continue;
                }


                String name =
                        clean(fields[0]);


                if (name == null) {
                    malformed++;
                    continue;
                }


                // ========================================
                // PREVENT DUPLICATES
                // ========================================

                if (
                        pesticideRepository
                                .findByNameIgnoreCase(name)
                                .isPresent()
                ) {

                    skipped++;

                    continue;
                }


                // ========================================
                // CREATE PESTICIDE
                // ========================================

                Pesticide pesticide =
                        new Pesticide();


                pesticide.setName(name);


                pesticide.setType(
                        valueOrDefault(
                                fields[1],
                                "Registered pesticide"
                        )
                );


                pesticide.setActiveIngredient(
                        clean(fields[2])
                );


                pesticide.setDescription(
                        clean(fields[3])
                );


                pesticide.setUses(
                        clean(fields[4])
                );


                /*
                 * IMPORTANT:
                 *
                 * Java property remains "crops"
                 * but database column is:
                 *
                 * suggested_crops
                 */

                pesticide.setCrops(
                        clean(fields[5])
                );


                pesticide.setTargetPests(
                        clean(fields[6])
                );


                pesticide.setSafetyMeasures(
                        clean(fields[7])
                );


                pesticide.setWarnings(
                        clean(fields[8])
                );


                // ========================================
                // SAVE
                // ========================================

                pesticideRepository.save(
                        pesticide
                );

                inserted++;
            }
        }


        // ========================================
        // IMPORT SUMMARY
        // ========================================

        System.out.println(
                "========================================"
        );

        System.out.println(
                "PPQS PESTICIDE IMPORT COMPLETE"
        );

        System.out.println(
                "Inserted : " + inserted
        );

        System.out.println(
                "Skipped  : " + skipped
        );

        System.out.println(
                "Malformed: " + malformed
        );

        System.out.println(
                "========================================"
        );
    }


    // ========================================
    // CLEAN VALUE
    // ========================================

    private String clean(
            String value
    ) {

        if (value == null) {
            return null;
        }


        String trimmed =
                value.trim();


        if (trimmed.isEmpty()) {
            return null;
        }


        return trimmed;
    }


    // ========================================
    // DEFAULT VALUE
    // ========================================

    private String valueOrDefault(
            String value,
            String defaultValue
    ) {

        String cleaned =
                clean(value);


        if (cleaned == null) {
            return defaultValue;
        }


        return cleaned;
    }


    // ========================================
    // CSV PARSER
    // ========================================

    private String[] parseCsvLine(
            String line
    ) {

        List<String> values =
                new ArrayList<>();


        StringBuilder current =
                new StringBuilder();


        boolean quoted = false;


        for (
                int i = 0;
                i < line.length();
                i++
        ) {

            char c =
                    line.charAt(i);


            if (c == '"') {

                if (
                        quoted
                                && i + 1 < line.length()
                                && line.charAt(i + 1) == '"'
                ) {

                    current.append('"');

                    i++;

                } else {

                    quoted = !quoted;
                }

            } else if (
                    c == ','
                            && !quoted
            ) {

                values.add(
                        current.toString()
                );

                current.setLength(0);

            } else {

                current.append(c);
            }
        }


        values.add(
                current.toString()
        );


        return values.toArray(
                new String[0]
        );
    }
}