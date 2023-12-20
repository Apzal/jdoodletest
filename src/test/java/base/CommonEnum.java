package base;

public class CommonEnum {

    public enum PATH{
        JAVA("online-java-compiler/");

        private final String value;
        PATH(String value){
            this.value = BasePage.BASE_URL+value;
        }
        public String getValue(){
            return value;
        }
    }
}
