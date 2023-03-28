<?php
get_header();
while (have_posts()) {
    the_post();
?>

    <div class="page-banner" style="margin-bottom :50px">
        <div class="page-banner__bg-image" style="background-image: url(<?php $pageBannerImage = get_field('page_banner_background_image');
                                                                        echo $pageBannerImage['sizes']['pageBanner'];; ?> );">
        </div>
        <div class="page-banner__content container container--narrow">
            <h1 class="page-banner__title">Past Events</h1>

            <div class="page-banner__intro">
                <p style="font-color:white"><?php the_title() ?></p>

            </div>
        </div>
    </div>

    <div>
        <div>
            <h1> <?php the_content(); ?> </h1>
        </div>
        <div style="text-align: center;">
            <img class="professor-card" src="<?php the_post_thumbnail_url('Landscape'); ?>" style="width: 50%; height: auto;">
        </div>
        <h1 class="event-services" style="margin-top:50px;text-align:center; "><b>Services Provided for this Event</b></h1>
    </div>

    <div class="posts-container">
        <?php
        // Get the values of the custom field with the key "relationship"
        $custom_field_values =  get_field('staff_member');

        // Output the custom field values if they exist
        if (!empty($custom_field_values)) {
            $related_post_ids = array();
            foreach ($custom_field_values as $value) {
                array_push($related_post_ids, $value->ID);
            }

            // Query only the related posts for the current post
            $args = array(
                'post_type' => 'staff_member',
                'post__in' => $related_post_ids,
                'orderby' => 'post__in',
            );
            $loop = new WP_Query($args);

            // Loop through each related post
            while ($loop->have_posts()) : $loop->the_post();
                // Add a new column div for every second post
                if ($loop->current_post % 2 == 0) {
                    echo '<div class="post-row">';
                }
                echo '<div class="post-cell">';
                echo '<img src="' . get_the_post_thumbnail_url($post->ID, 'front_page') . '" class="post-image">';
                echo '<div class="post-content">';
                echo '<b>' . '<h2>' . esc_html(get_the_title()) . '</h2>' . '</b>';
                echo '<p>' . esc_html(get_the_content()) . '</p>';
                echo '</div>';
                echo '</div>';
        ?>

        <?php
                // Close the div for every second post
                if (($loop->current_post + 1) % 2 == 0 || $loop->post_count == $loop->current_post + 1) {
                    echo '</div>';
                    echo '<hr>';
                }
            endwhile;
        }
        ?>
    </div>




<?php }
get_footer(); ?>