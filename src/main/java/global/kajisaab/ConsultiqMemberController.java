package global.kajisaab;

import io.micronaut.http.annotation.*;

@Controller("/consultiq-member")
public class ConsultiqMemberController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}