package com.github.asgardbot.commons;

public final class ServiceId {

    private String identifier;

    public ServiceId(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceId serviceId = (ServiceId)o;

        return identifier.equals(serviceId.identifier);
    }
}
