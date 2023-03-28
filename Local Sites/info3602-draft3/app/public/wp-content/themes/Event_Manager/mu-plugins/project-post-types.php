


<?php
function project_post_types()
{
    register_post_type('past-events', array(
        'supports' => array('title', 'editor', 'excerpt', 'custom-fields', 'thumbnail'),
        'rewrite' => array('slug' => 'past-events'),
        'has_archive' => true,
        'public' => true,
        'labels' => array(
            'name' => "Past-Events",
            'add_new_item' => 'Add Latest Past-Event',
            'edit_item' => 'Edit Past-Event',
            'all_items' => 'All Past-Events',
            'singular_name' => "Past-Event"
        ),
        'menu_icon' => 'dashicons-calendar'
    ));


    register_post_type('reviews', array(
        'supports' => array('title', 'editor', 'excerpt', 'custom-fields', 'thumbnail'),
        'rewrite' => array('slug' => 'reviews'),
        'has_archive' => true,
        'public' => true,
        'labels' => array(
            'name' => "Reviews",
            'add_new_item' => 'Add Latest Review',
            'edit_item' => 'Edit Review',
            'all_items' => 'All Review',
            'singular_name' => "Review"
        ),
        'menu_icon' => 'dashicons-awards'
    ));

    register_post_type('staff_member', array(
        'supports' => array('title', 'editor', 'excerpt', 'custom-fields', 'thumbnail'),
        'rewrite' => array('slug' => 'staff_member'),
        'has_archive' => true,
        'public' => true,
        'labels' => array(
            'name' => "Staff_Member",
            'add_new_item' => 'Add Latest Staff_Member',
            'edit_item' => 'Edit Staff_Member',
            'all_items' => 'All Staff_Member',
            'singular_name' => "Staff_Member"
        ),
        'menu_icon' => 'dashicons-welcome-learn-more'
    ));
}

add_action('init', 'project_post_types');
?>