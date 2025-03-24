package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.schedule.Session;

/**
 * Edits a scheduled session identified by its index.
 */
public class ScheduleEditCommand extends Command {

    public static final String COMMAND_WORD = "schedule edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits a scheduled session using its index and new details.\n"
            + "Example: schedule edit 1 n/NEW_NAME s/NEW_SUBJECT d/2025-03-19 t/14:00 dur/2h";

    public static final String MESSAGE_SUCCESS = "Edited session: %1$s";
    public static final String MESSAGE_INVALID_INDEX = "Invalid index! Please provide a valid session number.";
    public static final String MESSAGE_SESSION_NOT_FOUND = "The session could not be found.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditScheduleDescriptor editScheduleDescriptor;

    /**
     * Constructs a ScheduleEditCommand to edit a session at the given index.
     */
    public ScheduleEditCommand(Index index, EditScheduleDescriptor editScheduleDescriptor) {
        requireNonNull(index);
        requireNonNull(editScheduleDescriptor);

        this.index = index;
        this.editScheduleDescriptor = new EditScheduleDescriptor(editScheduleDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Session> lastShownList = model.getSessionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Session sessionToEdit = lastShownList.get(index.getZeroBased());
        Session editedSession = createEditedSession(sessionToEdit, editScheduleDescriptor);

        model.updateSession(sessionToEdit, editedSession);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedSession));
    }

    /**
     * Creates and returns a {@code Session} with the details of {@code sessionToEdit}
     * edited with {@code editScheduleDescriptor}.
     */
    private static Session createEditedSession(Session sessionToEdit, EditScheduleDescriptor editScheduleDescriptor) {
        assert sessionToEdit != null;

        String updatedName = editScheduleDescriptor.getName().orElse(sessionToEdit.getStudentName());
        String updatedSubject = editScheduleDescriptor.getSubject().orElse(sessionToEdit.getSubject());
        LocalDate updatedDate = editScheduleDescriptor.getDate().orElse(sessionToEdit.getDate());
        LocalTime updatedTime = editScheduleDescriptor.getTime().orElse(sessionToEdit.getTime());
        Duration updatedDuration = editScheduleDescriptor.getDuration().orElse(sessionToEdit.getDuration());

        return new Session(updatedName, updatedSubject, updatedDate, updatedTime, updatedDuration);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // Short circuit if same object
                || (other instanceof ScheduleEditCommand // Check instance type
                && index.equals(((ScheduleEditCommand) other).index)); // Compare index
    }

    /**
     * Stores the details to edit the session with. Each non-empty field value will replace the
     * corresponding field value of the session.
     */
    public static class EditScheduleDescriptor {
        private String name;
        private String subject;
        private LocalDate date;
        private LocalTime time;
        private Duration duration;

        public EditScheduleDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditScheduleDescriptor(EditScheduleDescriptor toCopy) {
            setName(toCopy.name);
            setSubject(toCopy.subject);
            setDate(toCopy.date);
            setTime(toCopy.time);
            setDuration(toCopy.duration);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, subject, date, time, duration);
        }

        public void setName(String name) {
            this.name = name;
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public Optional<String> getSubject() {
            return Optional.ofNullable(subject);
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setTime(LocalTime time) {
            this.time = time;
        }

        public Optional<LocalTime> getTime() {
            return Optional.ofNullable(time);
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public Optional<Duration> getDuration() {
            return Optional.ofNullable(duration);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditScheduleDescriptor)) {
                return false;
            }

            EditScheduleDescriptor otherEditScheduleDescriptor = (EditScheduleDescriptor) other;
            return Objects.equals(name, otherEditScheduleDescriptor.name)
                    && Objects.equals(subject, otherEditScheduleDescriptor.subject)
                    && Objects.equals(date, otherEditScheduleDescriptor.date)
                    && Objects.equals(time, otherEditScheduleDescriptor.time)
                    && Objects.equals(duration, otherEditScheduleDescriptor.duration);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("subject", subject)
                    .add("date", date)
                    .add("time", time)
                    .add("duration", duration)
                    .toString();
        }
    }
}
