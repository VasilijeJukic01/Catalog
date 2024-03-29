package com.example.catalog.repo

import com.example.catalog.model.Breed

val DataSample = listOf(
    Breed(
        id = "1",
        name = "Abyssinian",
        altNames = listOf("Aby"),
        description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
        temperament = listOf("Active", "Energetic", "Independent", "Intelligent", "Gentle")
    ),
    Breed(
        id = "2",
        name = "Aegean",
        altNames = listOf("Greek cats"),
        description = "Aegean cats are a naturally occurring landrace of domestic cat originating from the Cycladic Islands of Greece.",
        temperament = listOf("Affectionate", "Social", "Intelligent", "Playful", "Active")
    ),
)