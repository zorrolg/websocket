package net.paiyou.entity.enums;

import net.paiyou.entity.model.Card;

import java.util.Collection;

/**
 * 牌的单元的类型。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public interface CardUnitType {

	/**
	 * 返回一个单元中有几张牌。
	 */
	int size();

	/**
	 * 判断指定牌集合是否是合法的单元。
	 */
	boolean isLegalTiles(Collection<Card> tiles);

	/**
	 * 判断指定牌型集合是否是合法的单元。
	 */
	boolean isLegalTileTypes(Collection<CardWeight> types);

}