package seedu.nova.testutil;

import static seedu.nova.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

<<<<<<< HEAD:src/test/java/seedu/nova/testutil/PersonUtil.java
import seedu.nova.logic.commands.AddCommand;
import seedu.nova.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.nova.model.common.person.Person;
import seedu.nova.model.common.tag.Tag;
=======
import seedu.nova.logic.commands.AbAddCommand;
import seedu.nova.logic.commands.AbEditCommand.EditPersonDescriptor;
import seedu.nova.model.common.person.Person;
import seedu.nova.model.common.tag.Tag;
>>>>>>> c6c0bb78e07ef00942b0263e80b55d6c724c2c2b:src/test/java/seedu/address/testutil/PersonUtil.java

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AbAddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}