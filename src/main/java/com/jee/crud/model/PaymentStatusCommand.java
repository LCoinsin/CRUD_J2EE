package com.jee.crud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_status_command")
public class PaymentStatusCommand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state")
    private boolean state;

    public PaymentStatusCommand() {}

    public PaymentStatusCommand(Long id, boolean state) {
        this.id = id;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
