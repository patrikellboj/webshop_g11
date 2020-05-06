package ejb.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        String pass = (String)value; // castar value (ett Object) till en String.
        char[] chars = pass.toCharArray(); //gör om pass strängen till en array med chars
        boolean text = false;
        boolean numbers = false;

        for (char c : chars) {
            if(Character.isLetter(c)) {
                text = true;
            }

            if(Character.isDigit(c)) {
                numbers = true;
            }
        }

        if (chars.length < 4) {
            String charLength = "Lösenord måste innehålla minst 4 tecken";
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, charLength, charLength));
        }

        if(!text || !numbers) {
            String textAndDigit = "Lösenord måste innehålla både text och siffror";
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, textAndDigit, textAndDigit)); //FacesMessage -> Vad som ska visas i errormeddelande
        }

    }
}
