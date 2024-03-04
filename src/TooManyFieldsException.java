
@SuppressWarnings("serial")
public class TooManyFieldsException extends Exception {

    public TooManyFieldsException (String S) {
        super(S);
    }

    public TooManyFieldsException () {
        super();
    }
}