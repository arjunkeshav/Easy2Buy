package model;

public class AddProduct {
   private String productName,productCatog,productPrice,ProductDesc,productImage,productOffer,vendorId;

    public AddProduct(String productName, String productCatog, String productPrice, String productDesc, String productImage, String productOffer, String vendorId) {
        this.productName = productName;
        this.productCatog = productCatog;
        this.productPrice = productPrice;
        ProductDesc = productDesc;
        this.productImage = productImage;
        this.productOffer = productOffer;
        this.vendorId = vendorId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCatog() {
        return productCatog;
    }

    public void setProductCatog(String productCatog) {
        this.productCatog = productCatog;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDesc() {
        return ProductDesc;
    }

    public void setProductDesc(String productDesc) {
        ProductDesc = productDesc;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductOffer() {
        return productOffer;
    }

    public void setProductOffer(String productOffer) {
        this.productOffer = productOffer;
    }
}
