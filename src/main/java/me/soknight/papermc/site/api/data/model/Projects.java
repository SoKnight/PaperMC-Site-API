package me.soknight.papermc.site.api.data.model;

import me.soknight.papermc.site.api.annotation.ListWrappingModel;

import java.util.ArrayList;

@ListWrappingModel("projects")
public final class Projects extends ArrayList<String> implements PrettyPrintable {
}
