package net.milkycraft.ASEConfiguration;


import org.bukkit.configuration.file.YamlConfigurationOptions;

public class EggConfigurationOptions extends YamlConfigurationOptions {
	public EggConfigurationOptions(EggConfiguration configuration){
		super(configuration);
	}
	@Override
	public EggConfiguration configuration(){
		return (EggConfiguration) super.configuration();
	}
	@Override
	public EggConfigurationOptions copyDefaults(boolean value){
		super.copyDefaults(value);
		return this;
	}
	@Override
	public EggConfigurationOptions header(String value){
		super.header(value);
		return this;
	}
	//    /**
	//     * Allows case-insensitive lookups
	//     *
	//     * @param value True to turn off sensitivity
	//     * @return This Instance
	//     */
	//    public GoldConfigurationOptions lowercaseKeys(boolean value) {
	//        lowercaseKeys = value;
	//        return this;
	//    }
	//
	//    public boolean lowercaseKeys() {
	//        return lowercaseKeys;
	//    }

	/**
	 * 
	 * 
	 * @param lines Comma Separated strings to build into the header
	 * @return This Instance
	 */
	public EggConfigurationOptions header(String... lines){
		StringBuilder string = new StringBuilder();
		for(String s : lines){
			if(s == null)
				continue;
			if(string.length() > 0){
				string.append("\n");
			}
			string.append(s);
		}
		header(string.length() == 0 ? null : string.toString());
		return this;
	}
	@Override
	public EggConfigurationOptions copyHeader(boolean value){
		super.copyHeader(value);
		return this;
	}
	@Override
	public EggConfigurationOptions pathSeparator(char value){
		super.pathSeparator(value);
		return this;
	}
	@Override
	public EggConfigurationOptions indent(int value){
		super.indent(value);
		return this;
	}
}