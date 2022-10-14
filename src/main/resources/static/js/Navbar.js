import {AtlassianNavigation, PrimaryButton, ProductHome} from "@atlaskit/atlassian-navigation";
import {AtlassianIcon, AtlassianLogo} from "@atlaskit/logo";
import React from "react";


export const Navbar = () => {
    return (
        <AtlassianNavigation
            label="Requirement Yogi - Coding interview"
            primaryItems={[
                <PrimaryButton href={"/"}>Users</PrimaryButton>,
                <PrimaryButton href={"/requirements"}>Requirements</PrimaryButton>,
                <PrimaryButton href={"/requirements-vanilla"}>Requirements (HTML)</PrimaryButton>,
                <PrimaryButton href={"/page"}>ADF Page</PrimaryButton>
            ]}
            renderProductHome={() => "Requirement Yogi - Coding interview"}
        />
    );
};