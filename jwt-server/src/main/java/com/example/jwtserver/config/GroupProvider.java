package com.example.jwtserver.config;

import java.util.Arrays;
import java.util.Set;

import com.google.common.collect.Sets;

public final class GroupProvider {

    public static final Set<String> NON_PROD_GROUPS = Sets
            .newHashSet ( Arrays.asList ( "ptcfs-bladerunner-npe-site-admin", "ptcfs-bladerunner-npe-user", "ptcfs-bladerunner-npe-team-admin" ) );

    public static final Set<String> PROD_GROUPS = Sets
            .newHashSet ( Arrays.asList ( "ptcfs-bladerunner-prod-site-admin", "ptcfs-bladerunner-prod-team-admin", "ptcfs-bladerunner-prod-user" ) );
}
