package enroll_In_classes;

public class Product {
    private String url;
    private String image;
    private String name;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String price;

    // getters and setters omitted for brevity...

    @Override
    public String toString() {
        return "{ \"url\":\"" + url + "\", "
                + " \"image\": \"" + image + "\", "
                + "\"name\":\"" + name + "\", "
                + "\"price\": \"" + price + "\" }";
    }
}