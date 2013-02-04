#!/apollo/bin/env perl -w

use strict;
use lib '/apollo/lib';
use Amazon::Apollo::UseEnvironmentLib;
use Amazon::IhrMetrics::Utils;

my $util = Amazon::IhrMetrics::Utils->new();
$util->init();

print STDOUT "-------- Running ETL OihMetrics job --------\n";

my $root = $util->getRoot();
my $java_executable = $util->getJavaExecutable();
my $domain = $util->getDomain();
my $jvmargs = $util->getRuntimeArgs();
my $realm = $util->getRealm();
my $start_date = $util->getStartDate();
my $end_date = $util->getEndDate();
my $rundate = $util->getRunDate();
my $verbose = $util->getVerbose();
my $override_target_class = $util->getTargetClass();

if ($root){
  $ENV{"CORAL_CONFIG_PATH"} = "$root/coral-config";
}else{
  print "Can't find environment root path,CORAL_CONFIG_PATH is not set correctly";
}

if(! defined $jvmargs) {
    $jvmargs = "-Xmx2432m -Xms256m -Dfile.encoding=UTF-8 -Djavax.net.ssl.trustStore=certs/InternalTrustStore.jks -Djavax.net.ssl.trustStorePassword=amazon -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:CMSFullGCsBeforeCompaction=2 -XX:+UseCMSCompactAtFullCollection -XX:CMSInitiatingOccupancyFraction=80";
}

my $target_class = "com.amazon.invhealth.metrics.cli.Etl";
if (defined $override_target_class){
  $target_class = $override_target_class;
}

$jvmargs .= " -Djava.naming.factory.initial=amazon.platform.config.instance.AppConfigInitialContextFactory ";

#my $classpath = `find $root/lib -follow -name '*.jar' -printf '%h/%f:'`;
my $classpath = `find C:\mymise\myprojects\myutilities\pytool -name '*.jar' -printf '%h/%f:'`
$classpath .= `find $root/lib -follow -name '*.jar'  -and -not -name 'OihCommon.jar'  -printf  '%h/%f:'`
$classpath =~ s/:\s*$//;  # remove trailing colon
$classpath .= ":$root/var/hadoop/conf/";

my $command = "$java_executable $jvmargs -cp $classpath  $target_class --root $root --domain $domain --realm $realm --appgroup Oih --appname OihMetrics --start-date $start_date --end-date $end_date --rundate $rundate";

if (defined $verbose) {
    $command .= " --verbose";
}
$command .= " @ARGV";

print STDOUT "Executing command: $command\n";

exec($command) || die "Error: could not exec command";
