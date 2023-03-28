<?php
/*
Template Name: Corporate Event Page
*/
?>


<?php
get_header();
while (have_posts()) {
    the_post(); ?>

    <div class="page-banner">
        <div class="page-banner__bg-image" style="background-image: url(<?php $pageBannerImage = get_field('page_banner_background_image');
                                                                        echo $pageBannerImage['sizes']['pageBanner'];; ?> );">
        </div>
        <div class="page-banner__content container container--narrow">
            <h1 class="page-banner__title"><?php the_title() ?></h1>
            <div class="page-banner__intro">
                <p>Learn how the school of your dreams got started.</p>
            </div>
        </div>
    </div>

    <div class="container container--narrow page-section">

        <?php
        $theParent = wp_get_post_parent_ID(get_the_ID());
        if ($theParent) { ?>
            <div class="metabox metabox--position-up metabox--with-home-link">
                <p><a class="metabox__blog-home-link" href="<?php echo get_permalink($theParent) ?>">
                        <i class="fa fa-home" aria-hidden="true"></i> Back to <?php echo get_the_title($theParent)  ?></a>
                    <span class="metabox__main"><?php echo the_title(); ?></span>
                </p>
            </div>
        <?php
        }
        ?>
        <div class="generic-content">

            <div style="width: 50%; height: 600px; float: left; ">
                <p><?php echo the_content() ?></p>
            </div>

            <div style="width: 50%; height: 250px; float: right; margin-bottom: 150px;">
                <img src="<?php $pageImage1 = get_field('image1');
                            echo $pageImage1['url'];
                            ?>" style="width: 100%; height: 100%;">
            </div>

            <div style="width: 50%; height: 250px; float: right;">
                <img src="<?php $pageImage2 = get_field('image2');
                            echo $pageImage2['url'];
                            ?>" style="width: 100%; height: 100%;">
            </div>

            <div style=" clear: both;">
            </div>
            <div class="services">
                <h3 class="event-services" style="margin-top:50px;text-align:center; "><b>Our Corporate Event Services</b></h3>
                <div class="row1" style=margin-bottom:50px>
                    <div class="column1">
                        <div class="img_holder">
                            <img src="<?php echo get_theme_file_uri("/images/demo/entertainment.webp") ?>" alt="Image 1">
                        </div>
                        <h5><b>Entertainment</b></h5>
                        <p>Our in-house entertainment team includes everything from DJs to bands and professional dancers will interact
                            and engage so your attendees will remember the event for the rest of their lives.</p>
                    </div>
                    <div class="column1">
                        <div class="img_holder">
                            <img src="<?php echo get_theme_file_uri("/images/demo/lighting.jpg") ?>" alt="Image 2">
                        </div>
                        <h4><b>Lighting</b></h4>
                        <p>Whether it’s a high energy light show or elegant lighting, our event lighting has transformed your venue into
                            an unforgettable place for memories</p>
                    </div>
                    <div class="column1">
                        <div class="img_holder">
                            <img src="<?php echo get_theme_file_uri("/images/demo/sound.webp") ?>" alt="Image 3">
                        </div>
                        <h4><b>Sound</b></h4>
                        <p>From the microphones to the speakers to the instruments for the artists, our audio engineers will ensure that the
                            sound is perfectly balanced at your event no matter the size.</p>
                    </div>
                </div>

                <div class="row1" style=margin-bottom:50px>
                    <div class="column1">
                        <div class="img_holder">
                            <img src="<?php echo get_theme_file_uri("/images/demo/video.jpg") ?>" alt="Image 4">
                        </div>
                        <h4><b>Video</b></h4>
                        <p>Photos and video taken during the event become an aid to sharing the event experience through house organs and
                            social media, the printed media, or on a website. Event guests often desire the opportunity to obtain
                            photographs of an event as keepsakes, or to display in their own organizational and business communications.
                            Magma Event Bangkok offer you the best photographers and videographers Full HD recording and live streaming
                            on LED walls . we also can provide for you Drone services to make you event headline clip more attractive
                            and unforgettable</p>
                    </div>
                    <div class="column1">
                        <div class="img_holder">
                            <img src="<?php echo get_theme_file_uri("/images/demo/staging.jpg") ?>" alt="Image 5">
                        </div>
                        <h4><b>Staging</b></h4>
                        <p>Magma Event Bangkok will give your event a professional and exciting impression through our staging, unlimited
                            Design following your desires and your imagination, Combine with the last technology and materials we put your
                            event in the top of ambiance and atmosphere. we provide for you the best event production process in Bangkok
                            ,Thailand </p>
                    </div>
                    <div class="column1">
                        <div class="img_holder">
                            <img src="<?php echo get_theme_file_uri("/images/demo/special.jpg") ?>" alt="Image 6">
                        </div>
                        <h4><b>Special Effects</b></h4>
                        <p>CO2 Guns , smog Machines and Confetti shooters or Fireworks up to your choice will upgrade your event or concert
                            atmosphere . Magma Event Bangkok can offer you the last technology and the most competitive price to improve
                            your event .</p>
                    </div>
                </div>

                <div class="row1">
                    <div class="column1">
                        <div class="img_holder">
                            <img src="<?php echo get_theme_file_uri("/images/demo/decoration.jpg") ?>" alt="Image 7">
                        </div>
                        <h4><b>Decoration</b></h4>
                        <p>Decorations at your event its necessary for setting the mood, framing the emotion and underscoring the
                            importance of the event. From Tables wear to professionally-designed displays of color and beauty, decorations
                            generally work within a theme determined by event owners and our designers</p>
                    </div>
                    <div class="column1">
                        <div class="img_holder">
                            <img src="<?php echo get_theme_file_uri("/images/demo/design.jpg") ?> " alt=" Image 8">
                        </div>
                        <h4><b>Design</b></h4>
                        <p>Along with management experts, we are also the home of local talented creative’s designers working as team under
                            the same aim of generating unique event identity. Its start with Stage design as you can choose from two or
                            three option . Products Display and photos backdrops . Hall decoration or any elements that make your event
                            look the Best and special .</p>
                    </div>
                    <div class="column1">
                        <div class="img_holder">
                            <img src="<?php echo get_theme_file_uri("/images/demo/management.jpg") ?>" alt="Image 9">
                        </div>
                        <h4><b>Management</b></h4>
                        <p>Magma Events Bangkok is armed with a team of event management experts who have over 15 years of experience
                            generating distinctive and momentous experience for numerous international and domestic events.

                        </p>
                    </div>
                </div>

            </div>
        </div>
    </div>




<?php } ?>



<?php

get_footer();

?>