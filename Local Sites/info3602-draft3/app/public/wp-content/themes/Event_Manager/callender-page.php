<?php
/*
Template Name: Callender Page
*/
?>

<?php get_header() ?>

<?php
// Set the timezone
date_default_timezone_set('UTC');

// Set the year and month
$year = isset($_GET['year']) ? $_GET['year'] : date('Y');
$month = isset($_GET['month']) ? $_GET['month'] : date('m');

// Get the number of days in the month
$numDays = cal_days_in_month(CAL_GREGORIAN, $month, $year);

// Get the name of the month
$monthName = date('F', mktime(0, 0, 0, $month, 1, $year));

// Get the first day of the month
$firstDay = date('N', mktime(0, 0, 0, $month, 1, $year));

// Create an array of the days of the week
$daysOfWeek = array('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday');

// Set the path to the text file containing booked dates
$bookedDatesFilePath = get_template_directory() . '/the_booked_dates.json';

// Generate new dates if the file does not exist or is more than 24 hours old
if (!file_exists($bookedDatesFilePath) || time() - filemtime($bookedDatesFilePath) > 24 * 60 * 60) {
    // Create an array to store the booked dates for every month
    $bookedDates = array();
    for ($m = 1; $m <= 12; $m++) {
        // Get the number of days in the month
        $numDays = cal_days_in_month(CAL_GREGORIAN, $m, $year);

        // Set the booked dates for the month
        $bookedDates[$m] = array();
        for ($day = 1; $day <= $numDays; $day++) {
            $rand = rand(0, 10);
            if ($rand < 3) { // 30% chance of setting the date as BOOKED
                $bookedDates[$m][] = $day;
            }
        }
    }

    // Write the booked dates to the text file
    file_put_contents($bookedDatesFilePath, json_encode($bookedDates));
}

// Read the booked dates from the text file
$bookedDates = array();
if (file_exists($bookedDatesFilePath)) {
    $bookedDates = json_decode(file_get_contents($bookedDatesFilePath), true);
}

// Get the booked dates for the current month
$currentMonthBookedDates = isset($bookedDates[$month]) ? $bookedDates[$month] : array();

// Handle navigation between months
$prevYear = $year;
$prevMonth = $month - 1;
if ($prevMonth == 0) {
    $prevMonth = 12;
    $prevYear--;
}

$nextYear = $year;
$nextMonth = $month + 1;
if ($nextMonth == 13) {
    $nextMonth = 1;
    $nextYear++;
}
?>
<div class="calendar-container full-width">
    <div class="calendar-header">
        <a class="move" href="?year=<?php echo $prevYear; ?>&month=<?php echo $prevMonth; ?>">Previous</a>
        <h1><?php echo $monthName . ' ' . $year; ?></h1>
        <a class="move" href="?year=<?php echo $nextYear; ?>&month=<?php echo $nextMonth; ?>">Next</a>
    </div>
    <table class="calendar">
        <thead>
            <tr>
                <?php foreach ($daysOfWeek as $dayOfWeek) : ?>
                    <th><?php echo substr($dayOfWeek, 0, 2); ?></th>
                <?php endforeach; ?>
            </tr>
        </thead>
        <tbody>
            <?php
            $day = 1;
            $row = 1;
            while ($day <= $numDays) {
                echo '<tr>';
                for ($col = 1; $col <= 7; $col++) {
                    if ($row == 1 && $col < $firstDay) {
                        echo '<td></td>';
                    } else {
                        $booked = in_array($day, $currentMonthBookedDates);
                        if ($booked) {
                            echo "<td class='booked'>{$day}</td>";
                        } else {
                            echo "<td class='available'><a href='" . site_url('/form') . "'>Available</a></td>";
                        }
                        $day++;
                        if ($day > $numDays) {
                            break;
                        }
                    }
                }
                echo '</tr>';
                $row++;
            }
            ?>
        </tbody>
    </table>
</div>
<?php get_footer() ?>