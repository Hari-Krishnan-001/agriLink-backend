package com.agriconnect.config;

import com.agriconnect.entity.Pesticide;
import com.agriconnect.repository.PesticideRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PesticideDataInitializer
        implements CommandLineRunner {

    private final PesticideRepository pesticideRepository;


    // ========================================
    // CONSTRUCTOR
    // ========================================

    public PesticideDataInitializer(
            PesticideRepository pesticideRepository
    ) {

        this.pesticideRepository =
                pesticideRepository;
    }


    // ========================================
    // INITIALIZE DATA
    // ========================================

    @Override
    public void run(String... args) {

        if (pesticideRepository.count() > 0) {
            return;
        }


        // ====================================
        // CONFIDOR
        // ====================================

        Pesticide confidor =
                new Pesticide();

        confidor.setName("Confidor");

        confidor.setType("Insecticide");

        confidor.setActiveIngredient(
                "Imidacloprid"
        );

        confidor.setDescription(
                "An insecticide product used for " +
                        "management of certain insect pests. " +
                        "Always verify the registered product " +
                        "label and intended agricultural use."
        );

        confidor.setUses(
                "Management of selected insect pests " +
                        "affecting crops."
        );

        confidor.setCrops(
                "Crop suitability depends on the " +
                        "registered product label and local " +
                        "agricultural recommendations."
        );

        confidor.setTargetPests(
                "Selected sucking and other insect pests " +
                        "depending on the registered use."
        );

        confidor.setSafetyMeasures(
                "Read the complete product label before use. " +
                        "Wear the protective equipment specified " +
                        "on the label. Avoid unnecessary exposure " +
                        "and prevent contamination of water sources."
        );

        confidor.setWarnings(
                "Do not use outside the registered label. " +
                        "Follow local agricultural guidance and " +
                        "all label restrictions."
        );


        pesticideRepository.save(confidor);


        // ====================================
        // BAVISTIN
        // ====================================

        Pesticide bavistin =
                new Pesticide();

        bavistin.setName("Bavistin");

        bavistin.setType("Fungicide");

        bavistin.setActiveIngredient(
                "Carbendazim"
        );

        bavistin.setDescription(
                "A fungicide product used for management " +
                        "of certain fungal diseases. Product " +
                        "registration and permitted uses must be " +
                        "checked on the current label."
        );

        bavistin.setUses(
                "Management of selected fungal diseases " +
                        "in crops where the product is registered."
        );

        bavistin.setCrops(
                "Use only on crops listed on the registered " +
                        "product label."
        );

        bavistin.setTargetPests(
                "Selected fungal diseases."
        );

        bavistin.setSafetyMeasures(
                "Follow the registered label, required " +
                        "protective equipment, handling instructions, " +
                        "storage requirements, and disposal guidance."
        );

        bavistin.setWarnings(
                "Do not assume suitability for a crop or " +
                        "disease without checking the current label."
        );


        pesticideRepository.save(bavistin);


        // ====================================
        // DITHANE M-45
        // ====================================

        Pesticide dithane =
                new Pesticide();

        dithane.setName("Dithane M-45");

        dithane.setType("Fungicide");

        dithane.setActiveIngredient(
                "Mancozeb"
        );

        dithane.setDescription(
                "A fungicide product used for management " +
                        "of certain fungal diseases in agricultural crops."
        );

        dithane.setUses(
                "Management of selected fungal diseases " +
                        "where the product is registered."
        );

        dithane.setCrops(
                "Refer to the registered product label " +
                        "for approved crops and uses."
        );

        dithane.setTargetPests(
                "Selected fungal diseases."
        );

        dithane.setSafetyMeasures(
                "Follow the product label and use the " +
                        "protective equipment specified for handling " +
                        "and application."
        );

        dithane.setWarnings(
                "Use only according to registered directions " +
                        "and applicable local agricultural guidance."
        );


        pesticideRepository.save(dithane);


        // ====================================
        // ROUNDUP
        // ====================================

        Pesticide roundup =
                new Pesticide();

        roundup.setName("Roundup");

        roundup.setType("Herbicide");

        roundup.setActiveIngredient(
                "Glyphosate"
        );

        roundup.setDescription(
                "A herbicide product used to control certain " +
                        "unwanted plants. Exact permitted uses depend " +
                        "on the specific formulation and registration."
        );

        roundup.setUses(
                "Management of certain weeds and unwanted plants " +
                        "where the formulation is registered for that use."
        );

        roundup.setCrops(
                "Crop and application suitability must be " +
                        "confirmed from the specific registered label."
        );

        roundup.setTargetPests(
                "Selected weeds and unwanted plants."
        );

        roundup.setSafetyMeasures(
                "Prevent unintended contact with crops and " +
                        "non-target plants. Follow all label directions " +
                        "and protective-equipment requirements."
        );

        roundup.setWarnings(
                "Different formulations can have different " +
                        "registered uses. Check the exact product label."
        );


        pesticideRepository.save(roundup);


        // ====================================
        // 2,4-D
        // ====================================

        Pesticide twoFourD =
                new Pesticide();

        twoFourD.setName("2,4-D");

        twoFourD.setType("Herbicide");

        twoFourD.setActiveIngredient(
                "2,4-D"
        );

        twoFourD.setDescription(
                "A herbicide active ingredient used in " +
                        "certain formulations for management of weeds."
        );

        twoFourD.setUses(
                "Management of selected broadleaf weeds " +
                        "where the formulation is registered."
        );

        twoFourD.setCrops(
                "Use only on crops and situations listed " +
                        "on the specific registered product label."
        );

        twoFourD.setTargetPests(
                "Selected broadleaf weeds."
        );

        twoFourD.setSafetyMeasures(
                "Follow the exact product label. Prevent " +
                        "drift and unintended exposure to susceptible " +
                        "crops and non-target plants."
        );

        twoFourD.setWarnings(
                "Product formulations and permitted uses " +
                        "vary. Always verify the current label."
        );


        pesticideRepository.save(twoFourD);


        // ====================================
        // BT BIOLOGICAL
        // ====================================

        Pesticide bt =
                new Pesticide();

        bt.setName(
                "Bacillus thuringiensis"
        );

        bt.setType("Biological");

        bt.setActiveIngredient(
                "Bacillus thuringiensis"
        );

        bt.setDescription(
                "A biological pest-management agent used " +
                        "in suitable situations to manage certain " +
                        "insect larvae."
        );

        bt.setUses(
                "Biological management of selected insect larvae."
        );

        bt.setCrops(
                "Crop suitability depends on the registered " +
                        "product and its label."
        );

        bt.setTargetPests(
                "Selected susceptible insect larvae."
        );

        bt.setSafetyMeasures(
                "Follow the product label for handling, storage, " +
                        "protective equipment, and application."
        );

        bt.setWarnings(
                "Effectiveness depends on the target pest and " +
                        "correct timing. Use only according to the label."
        );


        pesticideRepository.save(bt);


        // ====================================
        // NEEM
        // ====================================

        Pesticide neem =
                new Pesticide();

        neem.setName("Neem Based Pesticide");

        neem.setType("Biological");

        neem.setActiveIngredient(
                "Neem-derived active compounds"
        );

        neem.setDescription(
                "Neem-based agricultural products can be used " +
                        "in suitable pest-management programs. The exact " +
                        "composition and permitted uses depend on the product."
        );

        neem.setUses(
                "Management of selected agricultural pests " +
                        "as permitted by the product label."
        );

        neem.setCrops(
                "Refer to the specific registered product label " +
                        "for approved crops."
        );

        neem.setTargetPests(
                "Selected agricultural insect pests."
        );

        neem.setSafetyMeasures(
                "Use according to the product label and follow " +
                        "the stated handling and protective-equipment requirements."
        );

        neem.setWarnings(
                "Neem products are not identical. Always check " +
                        "the exact product formulation and label."
        );


        pesticideRepository.save(neem);
    }
}