package com.business.finance_api.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "modalities")
public class ModalitiesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 100)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "modality", fetch = FetchType.LAZY)
    private List<InvestmentAllocationEntity> investmentAllocationEntities;

    protected ModalitiesEntity() {}

    public ModalitiesEntity(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<InvestmentAllocationEntity> getInvestmentAllocationEntities() {
        return investmentAllocationEntities;
    }

    public void setInvestmentAllocationEntities(List<InvestmentAllocationEntity> investmentAllocationEntities) {
        this.investmentAllocationEntities = investmentAllocationEntities;
    }
}
