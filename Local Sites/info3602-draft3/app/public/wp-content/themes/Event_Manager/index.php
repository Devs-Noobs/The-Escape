<?php get_header() ?>


<div class="page-banner">
<div class="page-banner__bg-image" style="background-image: url(<?php $pageBannerImage = get_field('page_banner_background_image');
        echo $pageBannerImage['sizes']['pageBanner'];;?> );">
        </div>
  <div class="page-banner__content container container--narrow">
    <h1 class="page-banner__title">Welcome to my Blog</h1>
    <div class="page-banner__intro">
      <p>Learn how the school of your dreams got started TESTING.</p>
    </div>
  </div>
</div>
<div class="container container--narrow page-section">
  <?php while (have_posts()) {
    the_post(); ?>

    <div class="post-item">
      <h2 class="headline headline--medium headline--post-title"><a href="<?php the_permalink() ?>">
          <?php the_title() ?>
        </a></h2>

      <div class="metabox">
        <P><?php echo the_time(), " ";
            echo get_the_category_list(', '); ?>
        </p>
      </div>

      <div class="generic-content">
        <P> <?php the_excerpt() ?></p>

      </div>

      <p><a class="btn btn--blue" href="<?php the_permalink(); ?>">Continue Reading <?php $raquo; ?> </a></p>

    </div>
  <?php }
  echo paginate_links(); ?>
</div>