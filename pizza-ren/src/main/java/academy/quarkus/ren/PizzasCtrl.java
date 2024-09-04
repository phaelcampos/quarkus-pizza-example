package academy.quarkus.ren;

import academy.quarkus.ren.data.IndexData;
import academy.quarkus.ren.data.SliderItem;
import io.quarkiverse.renarde.Controller;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

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
}
