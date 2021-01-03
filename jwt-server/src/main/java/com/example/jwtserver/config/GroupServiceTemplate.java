package com.example.jwtserver.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service("groupServiceTemplate")
public class GroupServiceTemplate {

    public Boolean nonProd(Authentication authentication) {
        Set<String> groups = getLoggedInUserRoles ( authentication );
        Set<String> validRoles = getValidGroupsFromLoggedInUser ( groups, GroupProvider.NON_PROD_GROUPS );
        return !validRoles.isEmpty ();
    }

    public Boolean prod(Authentication authentication) {
        Set<String> groups = getLoggedInUserRoles ( authentication );
        Set<String> validRoles = getValidGroupsFromLoggedInUser ( groups, GroupProvider.PROD_GROUPS );
        return !validRoles.isEmpty ();
    }

    public Boolean prodOrNonProd(Authentication authentication) {
        Set<String> groups = getLoggedInUserRoles ( authentication );
        Set<String> prodAndNoProd = new HashSet<> ( GroupProvider.PROD_GROUPS );
        prodAndNoProd.addAll ( GroupProvider.NON_PROD_GROUPS );
        Set<String> validRoles = getValidGroupsFromLoggedInUser ( groups, prodAndNoProd );
        return !validRoles.isEmpty ();
    }

    private Set<String> getLoggedInUserRoles(Authentication authentication) {
        Collection<? extends GrantedAuthority> clientProvidedRoles = authentication.getAuthorities();
        return clientProvidedRoles.stream ().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }

    private static Set<String> getValidGroupsFromLoggedInUser(Set<String> clientProvidedGroups, Set<String> authorizedGroups) {
        Set<String> validGroups = new HashSet<> ( authorizedGroups );
        validGroups.retainAll ( clientProvidedGroups );
        return validGroups;
    }
}
