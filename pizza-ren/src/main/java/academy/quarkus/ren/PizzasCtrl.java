package academy.quarkus.ren;

import academy.quarkus.ren.data.IndexData;
import academy.quarkus.ren.data.SliderItem;
import io.quarkiverse.renarde.Controller;
import io.quarkus.logging.Log;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.hibernate.validator.constraints.Length;
import org.jboss.resteasy.reactive.RestForm;

import java.util.List;


public class PizzasCtrl extends Controller {

    @CheckedTemplate
    static class Templates{
        public static native TemplateInstance index(AppContext context, IndexData data);
    }

    static List<SliderItem> sliderItems = List.of(
            new SliderItem(
                    "Crunchy",
                    "aaaaaaaaaaaaaaaa",
                    "aaaaaaaaaaaaaaaa",
                    "aaaaaaaaaaaaaaaa",
                    "aaaaaaaaaaaaaaaa",
                    "Images/bg_2.png"
            ),
            new SliderItem(
                    "BBBBBB",
                    "BBBBBB",
                    "BBBBBB",
                    "BBBBBB",
                    "BBBBBB",
                    "Images/bg_2.png"
            ),
            new SliderItem(
                    "Delicious",
                    "CCCCCCCC",
                    "CCCCCCCC",
                    "CCCCCCCC",
                    "CCCCCCCC",
                    "Images/bg_3.jpeg"
            )
    );

    @Inject
    AppContext context;

    @Path("/")
    @GET
    public TemplateInstance index(){
        return Templates.index(context, new IndexData(sliderItems));
    }


    @POST
    public void doSendMessage(
            @NotBlank @Length(min=2, max=10) @RestForm String firstName,
            @NotBlank @Length(min=2, max=10) @RestForm String lastName,
            @NotBlank @Length(min=2, max=10) @RestForm String message
    ){
        var profanity = message.contains("fuck");
        if(profanity){
            validation.addError("message", "Fala assim não!");
        }
        if (validation.hasErrors()) {
            validation.addError("someError", "Message failed, check your form data.");
        }
        if(validationFailed()){
            Log.infof("Validation failed for %s %s: \n %s",firstName, lastName,message);
        }
        Log.infof("Message from %s %s: \n %s", firstName, lastName, message);
        index();
    }
}
