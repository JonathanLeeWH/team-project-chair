package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.common.entity.Mail;
import sg.edu.nus.comp.cs3219.viz.logic.GateKeeper;
import sg.edu.nus.comp.cs3219.viz.logic.MailLogic;

import java.util.logging.Logger;

@RestController
public class MailController extends BaseRestController {
    private static final Logger log = Logger.getLogger(MailController.class.getSimpleName());

    private final MailLogic mailLogic;

    private final GateKeeper gateKeeper;

    public MailController(MailLogic mailLogic, GateKeeper gateKeeper) {
        this.mailLogic = mailLogic;
        this.gateKeeper = gateKeeper;
    }

    @PostMapping("/send-mail")
    public ResponseEntity<?> sendMail(@RequestBody Mail mailRequest) {
        UserInfo currentUser = gateKeeper.verifyLoginAccess();

        log.info(currentUser.getUserEmail() + " request to send mail received.");

        this.mailLogic.sendMessage(mailRequest);

        return ResponseEntity.ok().body("Mail has been sent successfully.");
    }
}