package model;

public class SupplierRequest {
    public String supplier;
    public String orderId;
    public RequestStatus status;

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public SupplierRequest() {
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public SupplierRequest(String supplier, String orderId, RequestStatus status) {
        this.supplier = supplier;
        this.orderId = orderId;
        this.status = status;
    };

}
