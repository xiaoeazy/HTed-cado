
package com.huan.HTed.core.i18n;

import java.text.MessageFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.AbstractMessageSource;

import com.huan.HTed.cache.impl.HashStringRedisCache;

/**
 * CacheMessageSource.
 */
public class CacheMessageSource extends AbstractMessageSource {
    private static final String SINGLE_QUOTES_REPLACEMENT = "&#39;";
    private static final String DOUBLE_QUOTES_REPLACEMENT = "&#34;";

    @Autowired
    @Qualifier("configCache")
    private HashStringRedisCache<String> configCache;

    public CacheMessageSource() {
        reload();
    }

    public void reload() {
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        return createMessageFormat(resolveCodeWithoutArguments(code, locale), locale);
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        String code2 = StringUtils.lowerCase(code);
        String pmt = configCache.getValue(code2 + "." + locale);
        if (pmt == null) {
            return StringUtils.contains(code, ".") ? StringUtils.substringAfterLast(code, ".") : code;
        }
        return replaceQuote(pmt);
    }

    private String replaceQuote(String str) {
        int idx = -1;
        char c = 0;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c == '\'' || c == '"') {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            return str;
        }
        StrBuilder sb = new StrBuilder(str.length() + 32);
        sb.append(str.substring(0, idx));
        if (c == '"') {
            sb.append(DOUBLE_QUOTES_REPLACEMENT);
        } else {
            sb.append(SINGLE_QUOTES_REPLACEMENT);
        }
        for (int i = idx + 1; i < str.length(); i++) {
            c = str.charAt(i);
            if (c == '"') {
                sb.append(DOUBLE_QUOTES_REPLACEMENT);
            } else if (c == '\'') {
                sb.append(SINGLE_QUOTES_REPLACEMENT);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
