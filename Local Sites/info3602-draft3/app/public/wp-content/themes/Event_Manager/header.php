<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
Template Name: Horizons
Author: <a href="http://www.os-templates.com/">OS Templates</a>
Author URI: http://www.os-templates.com/
Licence: Free to use under our free template licence terms
Licence URI: http://www.os-templates.com/template-terms
-->
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <?php wp_head(); ?>
    <title>Horizons</title>
    <meta http-equiv="Content-Type" content="width=device-width, charset=iso-8859-1" />
</head>

<body id="top">
    <div class="wrapper">
        <div id="header">
            <div id="logo">
                <h1><a href="<?php echo site_url("/home")?>">Horizons Event Management</a></h1>
                <p>Your Dream Event, Delivered</p>
            </div>
            <div id="topnav">

                <?php
                wp_nav_menu(array(
                    'theme_location' => 'headerMenuLocation',
                ));
                ?>

                
    <div class="site-header__util">
        <?php
            if(is_user_logged_in()){?>
            <a href="<?php echo wp_logout_url(); ?>" class="btn btn--small btn--dark-orange float-left btn--with-photo”>">
            <span class="site-header__avatar"><?php get_avatar(get_current_user_id(), 60); ?></span>
            <span class="btn__text">Log Out</span>
            </a>
            <?php    
            }
            else{ ?>
            <a href="<?php echo wp_login_url();?>" class="btn btn--small btn--orange float-left push-right">Login</a>
            <a href="<?php echo wp_registration_url(); ?>" class="btn btn--small btn--dark-orange float-left">Sign Up</a>

           
            <?php }
            ?>
        </div>


                <!--     <ul>
                    <li class="active"><a href="index.php">Home</a></li>
                    <li><a href="<?php site_url('/services') ?>">Services</a></li>
                    <li><a href="pages/full-width.html">Full Width</a></li>
                    <li><a href="#">Link Text</a></li>
                    <li class="last"><a href="#">A Long Link Text</a></li>
                </ul>-->
            </div>
            <br class="clear" />
        </div>
    </div>
