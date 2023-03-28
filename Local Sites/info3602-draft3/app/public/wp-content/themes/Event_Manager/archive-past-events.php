<?php get_header(); ?>

<div class="page-banner">
    <div class="page-banner__bg-image" style="background-image: url(<?php $pageBannerImage = get_field('page_banner_background_image');
                                                                    echo $pageBannerImage['sizes']['pageBanner'];;
                                                                    ?> );">
    </div>
    <div class="page-banner__content container container--narrow">
        <h1 class="page-banner__title"> <!-- hollow -->
            <?php
            echo "Past Events"
            ?>
        </h1>
        <div class="page-banner__intro">
            <p><?php echo "Come take a look at our past events" ?></p>
        </div>
    </div>
</div>
<div class="container container--narrow page-section">
    <?php while (have_posts()) {
        the_post(); ?>

        <div class="event-summary">
            <a href="<?php the_permalink() ?>">
                <div class="fl_left push-left">
                    <img class="professor-card__image  dynamic" src="<?php the_post_thumbnail_url('front_page'); ?>">
                </div>

            </a>
            <div class="event-summary__content">
                <h5 class="event-summary__title headline headline--tiny">
                    <a href="<?php the_permalink(); ?>"><?php the_title(); ?></a>
                </h5>
                <p><?php
                    if (has_excerpt()) the_excerpt();
                    else echo wp_trim_words(get_the_content(), 18);
                    ?>
                    <a href="<?php the_permalink(); ?>" class="nu gray">Read more</a>
                </p>
            </div>
        </div>
    <?php }
    echo paginate_links(); ?>
</div>



<?php


get_footer();
?>