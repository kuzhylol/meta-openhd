SUMMARY = "OpenHD: Open-source digital video transmission system"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "gitsm://github.com/openhd/OpenHD.git;protocol=https;branch=2.7-evo"
PV = "1.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit cmake pkgconfig systemd

DEPENDS = "flac poco libsodium gstreamer1.0 gstreamer1.0-plugins-base libpcap libusb1 libv4l"

RDEPENDS:${PN} += " \
    gstreamer1.0 \
    gstreamer1.0-libav \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-ugly \
    v4l-utils \
"

do_install:append() {
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${S}/systemd/openhd_mod.service ${D}${systemd_unitdir}/system/

    # OpenHD specific directories and files
    install -d ${D}/Video/
    install -d ${D}/boot/openhd/
    install -d ${D}${datadir}/openhd/
    touch ${D}${datadir}/openhd/licence
    touch ${D}/boot/openhd/air.txt
}

OECMAKE_SOURCEPATH = "${S}/OpenHD"

SYSTEMD_SERVICE:${PN} = "openhd_mod.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

FILES:${PN} += " \
    ${datadir}/openhd/licence \
    ${systemd_unitdir}/system/openhd.service \
    /Video \
    /boot/openhd/air.txt \
"
