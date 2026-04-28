package br.com.gva.quefominha.domain.enums;

public enum Status {

    PRODUCTION(1, "Em produção", false),
    DELIVERY(2, "Saiu para entrega", false),
    DONE(3, "Concluído", true);

    Status(int order, String description, boolean last) {
        this.order = order;
        this.description = description;
        this.last = last;
    }

    int order;
    String description;
    boolean last;

    public String getdescription() {
        return description;
    }

    public int getorder() {
        return order;
    }

    public boolean islast() {
        return last;
    }

    public static Status fromOrder(int order) {
        for (Status status : Status.values()) {
            if (status.getorder() == order) {
                return status;
            }
        }
        return null;
    }
}