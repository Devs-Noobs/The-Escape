<?php
get_header();
while (have_posts()) {
    the_post();

?>


    <div class="page-banner">
        <div class="page-banner__bg-image" style="background-image: url(<?php $pageBannerImage = get_field('page_banner_background_image');
        echo $pageBannerImage['sizes']['pageBanner'];;?> );">
        </div>
        <div class="page-banner__content container container--narrow">
            <h1 class="page-banner__title"><?php the_title() ?></h1>

            <!-- <div class="page-banner__intro">
                <p>Learn how the school of your dreams got started.</p>

            </div>-->


        </div>
</div>






        <div class="full-width-split group">

            <div class="full-width-split__one">
                <div class=" flat:left full-width-split__inner">

                    <div>
                        <?php
                        // Get the values of the custom field with the key "relationship"
                        $custom_field_values =  get_field('relationship');

                        // Output the custom field values if they exist
                        if (!empty($custom_field_values)) {
                            $related_post_ids = array();
                            foreach ($custom_field_values as $value) {
                                array_push($related_post_ids, $value->ID);
                            }

                            // Query only the related posts for the current post
                            $args = array(
                                'post_type' => 'past-events',
                                'post__in' => $related_post_ids,
                                'orderby' => 'post__in',
                            );
                            $loop = new WP_Query($args);

                            // Loop through each related post
                            while ($loop->have_posts()) : $loop->the_post();
                                echo '<h1 style="color: black;" >EVENT</h1>';
                                echo '<div>';
                                echo '<div class="professor-card__image">';
                                echo '<img class="rounded-image" src="' . esc_url(get_the_post_thumbnail_url($post->ID, 'front_page')) . '" alt="' . esc_attr(get_the_title($post->ID)) . '">';
                                echo '</div>';
                                echo '<div class="professor-card__text">';
                                echo '<h3 class="heading-size-2">' . esc_html(get_the_title()) . '</h3>';
                                echo '<p>' . esc_html(get_the_excerpt()) . '</p>';
                                echo '<a href="' . esc_url(get_permalink()) . '" class="btn btn--small btn--blue">Learn more</a>';
                                echo '</div>';
                                echo '</div>';

                            endwhile;

                            // Reset the post data to the main query
                            wp_reset_postdata();
                        }
                        ?>
                    </div>

                </div>



                <div class="full-width-split__two">

                    <div class="full-width-split__inner">
                        <!--
                        <div class="metabox metabox--position-up metabox--with-home-link">
                            <p><a class="metabox__blog-home-link" href="<?php echo site_url('/testimonials'); ?>">
                                    <i class="fa fa-home" aria-hidden="true"></i> Blog Home</a>
                                <span class="metabox__main">
                                    Reviews
                            </p>
                        </div>
                    -->



                        <h1>REVIEW</h1>
                        <div class="generic-content">



                            <h1 style="color:black"> <?php the_content(); ?> </h1>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<?php }
get_footer(); ?>