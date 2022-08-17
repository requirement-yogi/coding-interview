import {AtlassianNavigation, ProductHome} from "@atlaskit/atlassian-navigation";
import {AtlassianIcon, AtlassianLogo} from "@atlaskit/logo";
import React from "react";


export const Navbar = () => {
    return (
        <AtlassianNavigation
            label="site"
            primaryItems={[]}
            renderProductHome={() => <ProductHome icon={AtlassianIcon} logo={AtlassianLogo} />}
        />
    );
};