package com.example.demo.infrastructure.dtos;

import java.time.format.DateTimeFormatter;

import com.example.demo.infrastructure.jpa.models.EventEntity;

public class EventDTO {

    private Long id;
    private String name;
    private String date;
    private int totalSpots;
    private NewPartnerDTO partner;

    public EventDTO() {
    }

    public EventDTO(EventEntity event) {
        this.id = event.getId();
        this.name = event.getName();
        this.date = event.getDate().format(DateTimeFormatter.ISO_DATE);
        this.totalSpots = event.getTotalSpots();
        this.partner = new NewPartnerDTO(event.getPartner().getCnpj(), event.getPartner().getEmail(), event.getPartner().getName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public void setTotalSpots(int totalSpots) {
        this.totalSpots = totalSpots;
    }

    public NewPartnerDTO getPartner() {
        return partner;
    }

    public void setPartner(NewPartnerDTO partner) {
        this.partner = partner;
    }

}
