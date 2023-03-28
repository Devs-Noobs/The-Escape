<footer>
    <div id="footer">
        <div id="newsletter">
            <h2>Stay In The Know !</h2>
            <p>Please enter your email to join our mailing list</p>
            <form action="#" method="post">
                <fieldset>
                    <legend>News Letter</legend>
                    <input type="text" value="Enter Email Here&hellip;" onfocus="this.value=(this.value=='Enter Email Here&hellip;')? '' : this.value ;" />
                    <input type="submit" name="news_go" id="news_go" value="GO" />
                </fieldset>
            </form>
            <p>To unsubscribe please <a href="#">click here &raquo;</a></p>
        </div>
        <div class="footbox">
            <h2>Connect With Us</h2>
            <ul>
                <li><a href="#">Contact Support</a></li>
                <li><a href="#" class="social-color-twitter"><i class="fa fa-twitter" aria-hidden="true"></i></a></li>
                <li><a href="#" class="social-color-facebook"><i class="fa fa-facebook" aria-hidden="true"></i></a></li>
                <li><a href="#" class="social-color-instagram"><i class="fa fa-instagram" aria-hidden="true"></i></a></li>

            </ul>
        </div>


        <div class="footbox">
            <h2>Find Events</h2>
            <ul>
                <li><a href="#">North Region Credit Union Christmas Dinner</a></li>
                <li><a href="#">BMW X12 Prototype Launch</a></li>
                <li><a href="#">Caricom Annual Meeting</a></li>
            </ul>
        </div>




        <br class="clear" />

    </div>

    </div>

    <script>
        function displayAlert() {
            alert("Are you sure you want to submit the form?");
            return true; // Allow form submission
        }
    </script>
</footer>

<?php wp_footer(); ?>

</body>

</html>