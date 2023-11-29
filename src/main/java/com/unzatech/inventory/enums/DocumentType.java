package com.unzatech.inventory.enums;

public enum DocumentType {
    CI("Cédula de Identidad"),
    RUC("RUC"),
    OTRO("Otro");

    private final String description;

    DocumentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

