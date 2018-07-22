package com.vince.hearthstone.model;

public class Card {
    private String cardId;
    private String dbfId;
    private String name;
    private String cardSet;
    private String type;
    private String faction;
    private String rarity;
    private String img;
    private String imgGold;


    // Getter Methods

    public String getCardId() {
        return cardId;
    }

    public String getDbfId() {
        return dbfId;
    }

    public String getName() {
        return name;
    }

    public String getCardSet() {
        return cardSet;
    }

    public String getType() {
        return type;
    }

    public String getFaction() {
        return faction;
    }

    public String getRarity() {
        return rarity;
    }


    public String getImg() {
        return img;
    }

    public String getImgGold() {
        return imgGold;
    }


    // Setter Methods

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setDbfId(String dbfId) {
        this.dbfId = dbfId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }


    public void setImg(String img) {
        this.img = img;
    }

    public void setImgGold(String imgGold) {
        this.imgGold = imgGold;
    }


    @Override
    public String toString() {
        return "Card{" +
                "cardId='" + cardId + '\'' +
                ", dbfId='" + dbfId + '\'' +
                ", name='" + name + '\'' +
                ", cardSet='" + cardSet + '\'' +
                ", type='" + type + '\'' +
                ", faction='" + faction + '\'' +
                ", rarity='" + rarity + '\'' +
                ", img='" + img + '\'' +
                ", imgGold='" + imgGold + '\'' +
                '}';
    }
}
