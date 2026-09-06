SUMMARY = "Linux Device Drivers kernel modules"
DESCRIPTION = "LDD3 scull and misc kernel modules"
LICENSE = "Unknown"


SRC_URI = "git://github.com/salah-dex/assignment7-aesdsalah-dex-part2.git;protocol=https;branch=Master"
SRCREV = "50d1484c8d0d34fa0345d2188b6a5397463cc741"

S = "${WORKDIR}/git"

LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

EXTRA_OEMAKE += "KERNEL_SRC=${STAGING_KERNEL_DIR}"

inherit module

do_compile() {
    oe_runmake -C ${STAGING_KERNEL_DIR} \
        M=${S}/scull \
        EXTRA_CFLAGS="-I${S}/include" \
        modules

    oe_runmake -C ${STAGING_KERNEL_DIR} \
        M=${S}/misc-modules \
        EXTRA_CFLAGS="-I${S}/include" \
        modules
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/

   install -m 0644 ${S}/scull/scull.ko \
        ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/

    install -m 0644 ${S}/misc-modules/faulty.ko \
       ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/

    install -m 0644 ${S}/misc-modules/hello.ko \
        ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/
}

FILES:${PN} += "${nonarch_base_libdir}/modules/"

RPROVIDES:${PN} += "kernel-module-scull-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-faulty-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-hello-${KERNEL_VERSION}"


inherit update-rc.d

SRC_URI += "file://lddmodules"

INITSCRIPT_NAME = "lddmodules"
INITSCRIPT_PARAMS = "start 98 2 3 4 5 . stop 18 0 1 6 ."

do_install:append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/lddmodules \
        ${D}${sysconfdir}/init.d/lddmodules
}

FILES:${PN} += "${sysconfdir}/init.d/lddmodules"
