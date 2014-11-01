
package utils.formatting

/**
 *
 * @author Ricardo
 */
public  class MaskFunctions {
   
    
    def static formatString(String string, String mask)  {
        try {
            javax.swing.text.MaskFormatter mf = new javax.swing.text.MaskFormatter(mask.replace('9','#'));
            mf.setValueContainsLiteralCharacters(false);
            return mf.valueToString(string);
        }catch (Exception e){
            return ""
        }
    }
}

