package com.example.CoffeeShop.entity.event;

import com.example.CoffeeShop.entity.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "event")
public class OrderEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne()
    @JsonIgnore
    private Order order;
    @Column(name = "customer_id")
    @JsonIgnore
    private UUID customerId;
    @Column(name = "employee_id")
    @JsonIgnore
    private UUID employeeId;
    @Column(name = "delivery_time")
    @JsonIgnore
    private LocalDateTime deliveryTime;
    @Column(name = "product_id")
    @JsonIgnore
    private UUID productId;
    @Column(name = "product_cost")
    @JsonIgnore
    private long productCost;
    @Column(name = "time_stamp")
    private LocalDateTime timeStamp = LocalDateTime.now();
    @Column(name = "cancelled_reason")
    private String cancelledReason;
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    public OrderEvent() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public long getProductCost() {
        return productCost;
    }

    public void setProductCost(long productCost) {
        this.productCost = productCost;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setCancelledReason(String cancelledReason) {
        this.cancelledReason = cancelledReason;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    private OrderEvent(OrderEvent.Builder builder) {
        setOrder(builder.order);
        setCustomerId(builder.customerId);
        setEmployeeId(builder.employeeId);
        setDeliveryTime(builder.deliveryTime);
        setProductId(builder.productId);
        setProductCost(builder.productCost);
        setCancelledReason(builder.cancelledReason);
        setEventType(builder.eventType);
    }

    public static final class Builder {
        private Order order;
        private UUID customerId;
        private UUID employeeId;
        private LocalDateTime deliveryTime;
        private UUID productId;
        private long productCost;
        private String cancelledReason;
        private EventType eventType;

        public Builder() {
        }

        public OrderEvent.Builder withOrder(Order val) {
            order = val;
            return this;
        }

        public OrderEvent.Builder withCustomerId(UUID val) {
            customerId = val;
            return this;
        }

        public OrderEvent.Builder withEmployeeId(UUID val) {
            employeeId = val;
            return this;
        }

        public OrderEvent.Builder withDeliveryTime(LocalDateTime val) {
            deliveryTime = val;
            return this;
        }

        public OrderEvent.Builder withProductId(UUID val) {
            productId = val;
            return this;
        }

        public OrderEvent.Builder withProductCost(Long val) {
            productCost = val;
            return this;
        }


        public OrderEvent.Builder withCancelledReason(String val) {
            cancelledReason = val;
            return this;
        }

        public OrderEvent.Builder withEventType(EventType val) {
            eventType = val;
            return this;
        }

        public OrderEvent build() {
            return new OrderEvent(this);
        }
    }
}
