
package imagescroller;


public class ImageData {

    
    public ImageData(String path, String name){
               
       this.setName(name == null ? "" : name)
           .setPath(path == null ? "" : path);
    }
    public ImageData(){
        this.setName("")
            .setPath("");
    }
    
    public String getPath() {
        return path;
    }

    public ImageData setPath(String path) {
        this.path = path;
        return this;
    }

    public String getName() {
        return name;
    }

    public ImageData setName(String name) {
        this.name = name;
        return this;
    }

    
    private String path;
    private String name;
}
