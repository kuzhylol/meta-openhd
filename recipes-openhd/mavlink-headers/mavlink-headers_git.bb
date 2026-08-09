# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# Unable to find any files that looked like license statements. Check the accompanying
# documentation and source headers and set LICENSE and LIC_FILES_CHKSUM accordingly.
#
# NOTE: LICENSE is being set to "CLOSED" to allow you to at least start building - if
# this is not accurate with respect to the licensing of the software being built (it
# will not be in most cases) you must specify the correct value before using this
# recipe for anything other than initial testing/development!
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

SRC_URI = "git://github.com/OpenHD/mavlink-headers;protocol=https;branch=main"

# Modify these as desired
PV = "1.0+git"
SRCREV = "b131206ee2f90c563506ed74017c1da57210cca9"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    # Create the destination directory in the image
    install -d ${D}${includedir}/mavlink

    # Copy all header files from the 'generated' folder in the repo
    # Adjust the source path 'v2.0' if the repo structure is different
    cp -r ${S}/mavlink/v2.0/* ${D}${includedir}/mavlink/
}

# This is how you pass them to FILES properly
FILES_${PN}-dev += "${includedir}/mavlink"
