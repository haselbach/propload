package mrcode.propload.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import mrcode.propload.PropConf;
import mrcode.propload.PropLoad;
import mrcode.propload.PropResourceLoader;
import mrcode.propload.PropValue;
import mrcode.propload.PropValueParser;
import mrcode.propload.util.CombinatoricUtil;

/**
 * A property loader that makes an extensive search for property file using
 * the module name (to be provided), the host name, and the user name.
 * The exact resourcenames that are looked up are defined by getResourceNames();
 */
public class PropLoadExtensiveSearch implements PropLoad {

    private String moduleName;
    private String postfix = ".properties";
    
    private PropResourceLoader propResourceLoader = new PropResourceLoaderImpl(this.getClass());
    
    private PropValueParser propValueParser = new PropValueParserImpl();
    private PropConf defaultParent = null;
    
    public PropLoadExtensiveSearch() {}
    
    public PropLoadExtensiveSearch(String moduleName) {
        this.moduleName = moduleName;
    }
    
    public PropConf getPropConf() {
        PropConf propConf = defaultParent;
        for (String resourceName : getResourceNames()) {
            propConf = extendProp(propConf, resourceName);
        }
        
        return propConf;
    }

    protected Collection<String> getResourceNames() {
        final String user       = System.getProperty("user.name");
        final String home       = System.getProperty("user.home");
        final String workingDir = System.getProperty("user.dir");
        final String sep        = System.getProperty("file.separator");
        
        Collection<String> hostnames = getHostnames();
        String[] hostnamePart = new String[hostnames.size() + 1];
        hostnamePart[0] = "";
        int i = 1;
        for (String hostname : hostnames) {
            hostnamePart[i] = "." + hostname;
            i++;
        }
        
        Collection<String> resources = new LinkedHashSet<String>();
        resources.addAll(CombinatoricUtil.combineRightToLeft(new String[][] {
                new String[] { "classpath:" + moduleName },
                hostnamePart,
                new String[] { "", "." + user },
                new String[] { postfix }
        }));
        
        resources.addAll(CombinatoricUtil.combineRightToLeft(new String[][] {
                new String[] { "file:" + home + sep, "file:" + workingDir + sep },
                new String[] { "." + moduleName, moduleName },
                hostnamePart,
                new String[] { "", "." + user },
                new String[] { postfix }
        }));
        return resources;
    }
    
    protected PropConf extendProp(PropConf propConf, String location) {
        Properties properties = propResourceLoader.loadProperties(location);
        if (properties == null) {
            return propConf;
        }
        return extendPropFromProperties(propConf, properties);
    }
    
    protected PropConf extendPropFromProperties(PropConf propConf, Properties properties) {
        final Map<String, PropValue> propMap = new HashMap<String, PropValue>();
        for (Map.Entry entry : properties.entrySet()) {
            propMap.put((String)entry.getKey(), propValueParser.parsePropValue((String) entry.getValue()));
        }
        return new PropConfImpl(propConf, propMap);
    }
    
    protected static Collection<String> getHostnames() {
        Set<String> ipAddresses = new LinkedHashSet<String>();
        Set<String> hostnames = new LinkedHashSet<String>();
        String localhost;
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            localhost = inetAddress.getHostName();
        } catch (UnknownHostException e) {
            return null;
        }
        if (StringUtils.isEmpty(localhost)) {
            return null;
        }
        
        try {
            InetAddress[] addresses = InetAddress.getAllByName(localhost);
            for (InetAddress address : addresses) {
                ipAddresses.add(address.getHostAddress());
                hostnames.add(address.getHostName());
            }
        } catch (UnknownHostException e) {
        }
        
        ipAddresses.addAll(hostnames);
        return ipAddresses;
    }
    
    
    // Setters (for configuration)
    
    public void setDefaultParent(PropConf defaultParent) {
        this.defaultParent = defaultParent;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public void setPropResourceLoader(PropResourceLoader propResourceLoader) {
        this.propResourceLoader = propResourceLoader;
    }

    public void setPropValueParser(PropValueParser propValueParser) {
        this.propValueParser = propValueParser;
    }

}
