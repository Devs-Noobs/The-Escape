<?php
/*
Template Name: Form Page
*/

?>
<?php get_header() ?>

<div class="form-container">
    <form method="POST" onsubmit="return displayAlert();" action="<?php echo site_url('/home'); ?>">
        <label class="labeller" for="company_name">Company Name:</label>
        <input class="basic" type="text" id="company_name" name="company_name" required>
        <br><br><br>

        <label class="labeller" for="contact_info">Contact Information:</label>
        <input class="basic" type="text" id="contact_info" name="contact_info" required>
        <br><br><br>

        <label class="labeller" for="email">Email Address:</label>
        <input class="basic" type="email" id="email" name="email" required>
        <br> <br><br>

        <label class="labeller" for="email">Business Address:</label>
        <input class="basic" type="text" id="business_address" name="business_address" required>
        <br> <br><br>

        <label class="labeller" for="email">Preferred Location of Event:</label>
        <input class="basic" type="text" id="business_address" name="business_address" required>
        <br> <br><br>

        <label class="labeller" for="event_purpose">Purpose for the Event:</label>
        <br>
        <textarea id="event_purpose" name="event_purpose" required></textarea>
        <br><br><br><br>

        <label class="labeller" for="event_purpose">Any Special Requests:</label>
        <br>
        <textarea id="event_purpose" name=" special_requests" required></textarea>
        <br><br><br><br>

        <label class="labeller">Services Needed:</label>
        <ul>
            <li><input type="checkbox" id="catering" name="services" value="catering"><label for="catering">Catering</label></li>
            <li><input type="checkbox" id="transport" name="services" value="transport"><label for="transport">Transport</label></li>
            <li><input type="checkbox" id="lighting" name="services" value="lighting"><label for="lighting">Lighting</label></li>
            <li><input type="checkbox" id="sound" name="services" value="sound"><label for="sound">Sound</label></li>
            <li><input type="checkbox" id="video_photography" name="services" value="video_photography"><label for="video_photography">Video and Photography</label></li>
            <li><input type="checkbox" id="staging" name="services" value="staging"><label for="staging">Staging</label></li>
            <li><input type="checkbox" id="management" name="services" value="management"><label for="management">Management</label></li>
            <li><input type="checkbox" id="entertainment" name="services" value="entertainment"><label for="entertainment">Entertainment</label></li>
        </ul>

        <label class="labeller" for="event_date">Expected Date of the Event:</label>
        <input type="date" id="event_date" name="event_date" required>

        <label class="labeller" for="budget">Budget:</label>
        <input type="number" id="budget" name="budget" min="0" step="1000" required>
        <br><br><br>

        <input href="<?php echo site_url('/home') ?>" type="submit" value="Submit" class="submit-button">
    </form>
</div>



<?php get_footer() ?>