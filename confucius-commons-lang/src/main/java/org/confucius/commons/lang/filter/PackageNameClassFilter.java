/**
 * Confucius commons project
 */
package org.confucius.commons.lang.filter;

/**
 * {@link PackageNameClassFilter}
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see PackageNameClassFilter
 * @since 1.0.0 2016-02-23
 */
public class PackageNameClassFilter implements ClassFilter {

    private String packageName;
    private boolean includedSubPackages;
    private String subPackageNamePrefix;

    /**
     * Constructor
     *
     * @param packageName
     *         the name of package
     * @param includedSubPackages
     *         included sub-packages
     */
    public PackageNameClassFilter(String packageName, boolean includedSubPackages) {
        this.packageName = packageName;
        this.includedSubPackages = includedSubPackages;
        this.subPackageNamePrefix = includedSubPackages ? packageName + "." : null;
    }

    @Override
    public boolean accept(Class<?> filteredObject) {
        Package package_ = filteredObject.getPackage();
        String packageName = package_.getName();
        boolean accepted = packageName.equals(this.packageName);
        if (!accepted && includedSubPackages) {
            accepted = packageName.startsWith(subPackageNamePrefix);
        }
        return accepted;
    }
}
