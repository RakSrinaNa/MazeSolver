package fr.mrcraftcod.mazesolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/02/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-02-25
 */
public class SortedArrayList<T> extends ArrayList<T>
{
	private final Comparator<T> comparator;

	public SortedArrayList(Comparator<T> comparator)
	{
		this.comparator = comparator;
	}

	public void insertSorted(T value)
	{
		add(value);
		if(comparator != null)
			for (int i = size() - 1; i > 0 && comparator.compare(value, get(i - 1)) < 0; i--)
				Collections.swap(this, i, i - 1);
	}
}
