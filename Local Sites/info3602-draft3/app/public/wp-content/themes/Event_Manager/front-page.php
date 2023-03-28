<?php get_header() ?>



<div class="hero-slider">
    <div class="hero-slider__slide" style="height: auto; background-image: url(<?php echo get_theme_file_uri('images/demo/corporate-events.jpg') ?>);">
        <div class=" hero-slider__interior container">
            <div class="hero-slider__overlay">
                <h2 class="headline headline--medium t-center">Country Wide Event Planner</h2>
                <p class="t-center">One Stop Event Providers.</p>

            </div>
        </div>
    </div>
    <div class="hero-slider__slide" style="height: auto; background-image: url(<?php echo get_theme_file_uri('images/demo/the_wedding.jpg'); ?>)">
        <div class=" hero-slider__interior container">
            <div class="hero-slider__overlay">
                <h2 class="headline headline--medium t-center">Event Planning solutions with imaginative design</h2>
                <p class="t-center">Conception, Creation and production of events.</p>

            </div>
        </div>
    </div>
    <div class="hero-slider__slide" style="height: auto; background-image: url(<?php echo get_theme_file_uri('images/demo/live-show-events.webp'); ?>)">
        <div class="hero-slider__interior container">
            <div class="hero-slider__overlay">
                <h2 class="headline headline--medium t-center">Visit Our Professionals</h2>
                <p class="t-center">Every Event is a live and Unforgettable show.</p>

            </div>
        </div>
    </div>
</div>




<br class="clear" />



<div class="full-width-split group">
    <div class="full-width-split__one">
        <div class="full-width-split__inner">
            <h2 class="headline headline--small-plus t-center">Past Events </h2>

            <!--Custom Query-->
            <?php
            $homepageEvents = new WP_Query(array(
                'posts_per_page' => 2,
                'post_type' => 'past-events'
            ));
            while ($homepageEvents->have_posts()) {
                $homepageEvents->the_post(); ?>

                <div class="event-summary">
                    <a href="<?php the_permalink() ?>">
                        <div class="fl_left push-left">
                            <img class="professor-card__image rounded-image" src="<?php the_post_thumbnail_url('front_page'); ?>">
                        </div>

                    </a>
                    <div class="event-summary__content ">
                        <h5 class="event-summary__title headline headline--tiny"><a href="<?php the_permalink() ?>"><?php the_title(); ?> </a></h5>
                        <p><?php
                            if (has_excerpt()) echo get_the_excerpt();
                            else echo wp_trim_words(get_the_content(), 18);
                            ?>
                            <a href="<?php the_permalink(); ?>" class="nu gray">Learn more</a>
                        </p>
                    </div>
                </div>
            <?php

            }
            wp_reset_postdata();
            wp_reset_query();
            ?>



            <p class="t-center no-margin"><a href="<?php echo get_post_type_archive_link('past-events') ?>" class="btn btn--blue">View All Events</a></p>

        </div>
    </div>

    <div class="full-width-split__two">
        <div class="full-width-split__inner">
            <h2 class="headline headline--small-plus t-center"> Our Reviews</h2>


            <?php $homepagePosts = new WP_Query(array(
                'posts_per_page' => 2,
                'post_in' => array(3, 7, 12)

            ));
            while ($homepagePosts->have_posts()) {
                $homepagePosts->the_post(); ?>
                <li><?php the_title(); ?></li>
                <div class="event-summary">
                    <a class="event-summary__date event-summary__date--beige t-center" href="<?php the_permalink() ?>">
                        <span class="event-summary__month">
                            <?php the_time('M') ?>
                        </span>
                        <span class="event-summary__day">
                            <?php the_time('d') ?>
                        </span>
                    </a>
                    <div class="event-summary__content">
                        <h5 class="event-summary__title headline headline--tiny"><a href="<?php the_permalink() ?>"><?php the_title() ?></a></h5>
                        <p><?php
                            if (has_excerpt()) echo get_the_excerpt();
                            else echo wp_trim_words(get_the_content(), 18);
                            ?> <a href="<?php the_permalink() ?>" class="nu gray">Read more</a></p>
                    </div>
                </div>

            <?php } ?>


            <p class="t-center no-margin"><a href="<?php echo site_url('/testimonials') ?>" class="btn btn--yellow">View All Blog Posts</a></p>

        </div>
    </div>
</div>

<div class="wrapper">
    <?php

    get_footer();
    ?>