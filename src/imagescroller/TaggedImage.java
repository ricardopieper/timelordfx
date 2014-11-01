
package imagescroller;


public class TaggedImage<T> extends ImageData {

    private T tag;
    
    public T getTag(){
        return tag;
    }
    
    
    public TaggedImage<T> setTag(T tag){
        
        this.tag = tag;
        return this;
    }
    
    public TaggedImage(String path, String name, T obj){
        super(path, name);
        setTag(obj);
    } 
}
