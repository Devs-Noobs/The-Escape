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

    </div>
<?php } ?>

<div class="generic-content">
    <div class="row">
        <div class="col">
            <a href="<?php echo site_url('/event-production') ?>">
                <img src="<?php echo get_theme_file_uri("/images/demo/Event_Production.jpg") ?>">
                <div class="banner">Event Production</div>
            </a>
        </div>
        <div class="col">
            <a href="<?php echo site_url('/corporate-meetings') ?>">
                <img src="<?php echo get_theme_file_uri("/images/demo/Corporate-Events-DJ.jpg") ?>">
                <div class="banner">Corporate Events</div>
            </a>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <a href="<?php echo site_url('/motivation-tour') ?>">
                <img src="<?php echo get_theme_file_uri("/images/demo/Motivational-Tour.jpg") ?>">
                <div class="banner">Motivational Tour</div>
            </a>
        </div>
        <div class="col">
            <a href="<?php echo site_url('/product-launch') ?>">
                <img src="<?php echo get_theme_file_uri("/images/demo/Product_Launch.jpg") ?>">
                <div class="banner">Production Launch</div>
            </a>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <a href="<?php echo site_url('/team-building') ?>">
                <img src="<?php echo get_theme_file_uri("/images/demo/Team-Building.jpg") ?>">
                <div class="banner">Team Building</div>
            </a>
        </div>
        <div class="col">
            <a href="<?php echo site_url('/concert') ?>">
                <img src="<?php echo get_theme_file_uri("/images/demo/concert.jpg") ?>">
                <div class="banner">Concert</div>
            </a>
        </div>
    </div>
</div>



<?php

get_footer();

?>