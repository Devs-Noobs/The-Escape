

<?php
function project_files()
{

    wp_enqueue_style('project_main_styles', get_stylesheet_uri());
    //nickname for stylesheet, 

    wp_enqueue_script('project-js', get_theme_file_uri('/js/scripts-bundled.js'), NULL, '1.0', true); //creates slideshow of pics
    wp_enqueue_style('font-awesome', 'https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css');
}

add_action('wp_enqueue_scripts', 'project_files'); //allows for images to turn to slideshow , and adds all css


function university_features()
{
    add_theme_support('title-tag'); //actual function which adds unique title tab for each page

    //this tells Wordpress to support feature images
    add_theme_support('post-thumbnails');

    add_image_size('Landscape', 400, 260, true);
    add_image_size('front_page', 200, 130, true);
    add_image_size('Portrait', 180, 650, true);

    add_image_size('pageBanner', 1500, 350, true);
}

function redirectSubsToFrontend()
{
    $ourCurrentUser = wp_get_current_user();
    $userNumRoles = count($ourCurrentUser->roles);
    $userRole = $ourCurrentUser->roles[0];
    if ($userNumRoles == 1 and $userRole == 'subscriber') {
        wp_redirect(site_url('/'));
        exit; //tell PHP to stop once someone is redirected
    }
}

//Redirect subscriber accounts out of admin and onto homepage
add_action('admin_init', 'redirectSubsToFrontend');

function noSubsAdminBar()
{
    $ourCurrentUser = wp_get_current_user();
    $userNumRoles = count($ourCurrentUser->roles);
    $userRole = $ourCurrentUser->roles[0];
    if ($userNumRoles == 1 and $userRole == 'subscriber') {
        show_admin_bar(false);
    }
}

//Hide admin bar from subscribers
add_action('wp_loaded', 'noSubsAdminBar');

add_filter('login_headerurl', 'ourHeaderUrl');
function ourHeaderUrl()
{
    return esc_url(site_url('/'));
}

//load custom CSS on login screen
add_action('login_enqueue_scripts', 'ourLoginCSS');
function ourLoginCSS()
{
    wp_enqueue_style('project_main_styles', get_stylesheet_uri());
    wp_enqueue_style('custom-google-font', 'https://fonts.googleapis.com/css?
family=Roboto+Condensed:300,300i,400,400i,700,700i|Roboto:100,300,400,400i,700,700i');
}
add_action('after_setup_theme', 'university_features'); //generates a unique title for each page

register_nav_menu('headerMenuLocation', 'Header Menu Location');

?>