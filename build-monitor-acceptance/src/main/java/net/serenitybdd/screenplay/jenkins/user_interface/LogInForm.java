package net.serenitybdd.screenplay.jenkins.user_interface;

import net.serenitybdd.screenplay.targets.Target;

public class LogInForm {
    public static final Target Username_Field = Target.the("the 'User' field").locatedBy("//input[@name='j_username']");
    public static final Target Password_Field = Target.the("the 'Password' field").locatedBy("//input[@name='j_password']");
    public static final Target Log_In_Buttton = Target.the("the 'Sign In' button").locatedBy("//input[@type='submit']");
}
