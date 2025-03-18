package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_PARENTPHONE = new Prefix("pp/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_APPEND = new Prefix("-a");
    public static final Prefix PREFIX_OVERWRITE = new Prefix("-o");
    public static final Prefix PREFIX_CLEAR = new Prefix("-c");
    public static final Prefix PREFIX_SUBJECT = new Prefix("s/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_TIME = new Prefix("t/");
    public static final Prefix PREFIX_DURATION = new Prefix("dur/");
}
